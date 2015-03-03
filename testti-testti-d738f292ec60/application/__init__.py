"""
Initialize Flask app

"""
from flask import Flask, render_template, flash, request, g, redirect, url_for
import flask
import hashlib
import datetime
import requests
import os
from flask.ext.login import UserMixin
from werkzeug.datastructures import ImmutableOrderedMultiDict
import uuid
from wtforms.validators import Required
from flask.ext.sqlalchemy import SQLAlchemy
from flask_debugtoolbar import DebugToolbarExtension
from flask.ext.mail import Mail, Message
from werkzeug.debug import DebuggedApplication
from flask.ext.restless import ProcessingException
from flask.ext.login import current_user, login_user, LoginManager, logout_user,login_required
from flask.ext.wtf import Form
from wtforms import PasswordField, SubmitField, StringField, BooleanField
app = Flask('application')
db_url ="mysql+gaerdbms:///testtiweb?instance=testtiweb:datas"
app.config['SQLALCHEMY_DATABASE_URI'] = db_url

db = SQLAlchemy(app)
app.config['MAIL_USE_APPENGINE'] = True
mail = Mail(app)


def auth_test(**kw):
    if not current_user.is_authenticated() or current_user.status == 'N':
        raise ProcessingException(description='Not Authorized',
                                  code=401)
manager = flask.ext.restless.APIManager(app, flask_sqlalchemy_db=db)
login_manager = LoginManager()
login_manager.init_app(app)


class LoginForm(Form):
    email = StringField('email')
    password = PasswordField('password')
    remember_me = BooleanField('remember')
    submit = SubmitField('Login')


class SearchForm(Form):
    search = StringField('search', validators=[Required()])


class SignupForm(Form):
    username = StringField('username')
    password = PasswordField('password')
    repassword = PasswordField('confirm')
    email = StringField('email')
    submit = SubmitField('Sign Up')


@login_manager.user_loader
def load_user(user_id):
    return User.query.get(user_id)


@app.route('/start')
def start():
    if current_user.is_authenticated():
        user_id = current_user.id
        if current_user.status == "temp":
            if (datetime.date.today() - current_user.date).days > 10:
                user = User.query.filter_by(id=user_id).first()
                user.status = "N"
                current_user.status = "N"
                db.session.commit()
        if current_user.status == "N":
            return render_template('pricing.html', user=current_user)
        return render_template('testPageWithTimer.html', user_id=user_id)
    flash('You should log in first.')
    return redirect(url_for('home'))


@app.route('/paypal/', methods=['POST'])
def ipn():
    arg = ''
    #: We use an ImmutableOrderedMultiDict item because it retains the order.
    request.parameter_storage_class = ImmutableOrderedMultiDict
    values = request.form
    for x, y in values.iteritems():
        arg += "&{x}={y}".format(x=x,y=y)

    validate_url = 'https://www.sandbox.paypal.com' \
                   '/cgi-bin/webscr?cmd=_notify-validate{arg}' \
        .format(arg=arg)

    print 'Validating IPN using {url}'.format(url=validate_url)

    user_id = values.get('custom')
    r = requests.get(validate_url)

    if r.text == 'VERIFIED':
        user = User.query.filter_by(id=user_id).first()
        user.status = "Y"
        db.session.commit()
        flash('You have paid successfully.')
    else:
        flash('You have paid failed.')
    return r.text


@app.route('/search/<int:num>', methods=['POST'])
def search(num):
    user_id = current_user.id
    if current_user.status == "temp":
        if (datetime.date.today() - current_user.date()) > 10:
            user = User.query(id=user_id).first()
            user.status = "N"
            current_user.status = "N"
            db.session.commit()
    if current_user.status == "N":
        return render_template('pricing.html', user=current_user)
    search_subject = g.search_form.search.data
    return render_template('search_result.html', keywords=search_subject, page=num)


@app.route('/login', methods=['post', 'get'])
def sign_in():
    form = LoginForm()
    if form.validate_on_submit():
        email = form.email.data
        password = hashlib.new("md5", form.password.data).hexdigest()
        remember = form.remember_me.data
        users = User.query.filter_by(email=email, password=password).all()
        if len(users) > 0:
            user = users[0]
            login_user(user, remember=remember)
            flash('user '+email+' has logged in.')
            g.search_form = SearchForm()
            return render_template('hello.html', user=user)
        flash('user has failed to log in.')
    return render_template('login.html', form=form)

@app.route('/record/<sub_id>')
def record(sub_id):
    subject = Subject.query.get(sub_id)
    user = User.query.get(current_user.id)
    flag = request.args.get('flag')
    if flag == 'true':
        subject.correct += 1
        user.correct += 1
    subject.finished += 1
    user.finished += 1
    is_users_in = False
    users = subject.users
    for u in users:
        if u == current_user:
            is_users_in = True
    if not is_users_in:
        users.append(current_user)
    db.session.commit()
    return "success"


@app.route('/sign_out')
def sign_out():
    logout_user()
    form = LoginForm()
    return render_template('home.html', form=form)

@app.route('/signup', methods=['get', 'post'])
def signup():
    s_form = SignupForm()
    if s_form.validate_on_submit():
        username = s_form.username.data
        password = s_form.password.data
        repassword = s_form.repassword.data
        email = s_form.email.data
        if password != repassword:
            return
        user = User(username, password, email);
        db.session.add(user)
        db.session.commit()
        msg = Message('Test', sender=app.config['ADMINS'][0], recipients=[email])
        msg.body = 'text body'
        msg.html = '<b>html</b> body'
        mail.send(msg)
        flash('pleasec check ')
        return render_template('home.html')
    else:
        return render_template('signup.html', s_form=s_form)

@app.route('/api/get-user-info')
def get_user_info():
    if current_user.is_authenticated():
        return render_template('userinfo.html', user=current_user)


@app.route('/api/modify_password')
def modify_password():
    pass

@app.route('/user/<username>')
def show_user_profile(username=None):
    return render_template('hello.html', name=username)

@app.route('/')
def home():
    form = LoginForm()
    return render_template('home.html', form=form)


@app.route('/index')
def index():
    if current_user.is_authenticated():
        return render_template('hello.html', user=current_user)
    form = LoginForm()
    return render_template('login.html', form=form)


@app.before_request
def before_request():
    g.user = current_user
    if current_user.is_authenticated():
        g.search_form = SearchForm()



solved = db.Table('solved',
                  db.Column('user_id', db.String(32), db.ForeignKey('user.id')),
                  db.Column('sub_id', db.String(32), db.ForeignKey('subject.id')))


class Image(db.Model):
    id = db.Column(db.Unicode(32), primary_key=True)
    sub_id = db.Column(db.Unicode(32), db.ForeignKey('subject.id'))
    url = db.Column(db.String(100))
    url_type = db.Column(db.String(2))

    def __init__(self, sub_id, url, url_type):
        self.id = unicode(uuid.uuid1())
        self.sub_id = sub_id
        self.url = url
        self.url_type = url_type

    def __repr__(self):
        return 'Image :url %r url type %r' % (self.url, self.url_type)


class Category(db.Model):
    id = db.Column(db.Unicode(32), primary_key=True)
    subjects = db.relationship('Subject', backref='category', lazy='dynamic')
    name = db.Column(db.String(50), unique=True)

    def __init__(self, name):
        self.id = uuid.uuid1()
        self.name = name


class Subject(db.Model):
    id = db.Column(db.Unicode(32), primary_key=True)
    contents = db.Column(db.String(700))
    answer = db.Column(db.String(2))
    option1 = db.Column(db.String(300))
    images = db.relationship('Image', backref='subject', lazy='dynamic')
    option2 = db.Column(db.String(300))
    option3 = db.Column(db.String(300))
    option4 = db.Column(db.String(300))
    finished = db.Column(db.Integer)
    correct = db.Column(db.Integer)
    cat_id = db.Column(db.Unicode(32), db.ForeignKey('category.id'))

    def __init__(self, row):
        self.id = unicode(row[0])
        self.contents = row[1]
        self.answer = row[3]
        self.option1 = row[4]
        self.option2 = row[5]
        self.option3 = row[6]
        self.option4 = row[7]
        self.finished = 0
        self.correct = 0

    def __repr__(self):
        return 'subject %r' % self.contents


class User(db.Model, UserMixin):
    id = db.Column(db.Unicode(32), primary_key=True)
    username = db.Column(db.String(80), unique=True)
    password = db.Column(db.Unicode(32))
    email = db.Column(db.String(120), unique=True)
    finished = db.Column(db.Integer)
    correct = db.Column(db.Integer)
    status = db.Column(db.String(10))
    date = db.Column(db.Date)
    subjects = db.relationship('Subject', secondary=solved,
                               backref=db.backref('users', lazy='dynamic'))

    def __init__(self, username, password, email):
        self.id = unicode(uuid.uuid1())
        self.username = username
        self.password = hashlib.new("md5", password).hexdigest()
        self.email = email
        self.finished = 0
        self.correct = 0
        self.status = "temp"
        self.date = datetime.date.today()

    def __repr__(self):
        return 'User id %r email %r' % (self.id, self.email)


manager.create_api(Subject, methods=['GET', 'POST', 'DELETE'], preprocessors={
    'GET_SINGLE': [auth_test],
    'GET_MANY': [auth_test]
})

if os.getenv('FLASK_CONF') == 'DEV':
    app.config.from_object('application.settings.Development')
    toolbar = DebugToolbarExtension(app)
    
    # Google app engine mini profiler
    # https://github.com/kamens/gae_mini_profiler
    app.wsgi_app = DebuggedApplication(app.wsgi_app, evalex=True)

    from gae_mini_profiler import profiler, templatetags 
    @app.context_processor
    def inject_profiler():
        return dict(profiler_includes=templatetags.profiler_includes())
    app.wsgi_app = profiler.ProfilerWSGIMiddleware(app.wsgi_app)

elif os.getenv('FLASK_CONF') == 'TEST':
    app.config.from_object('application.settings.Testing')

else:
    app.config.from_object('application.settings.Production')

# Enable jinja2 loop controls extension
app.jinja_env.add_extension('jinja2.ext.loopcontrols')

# Pull in URL dispatch routes
import urls