"""
models.py

App Engine datastore models

"""


from google.appengine.ext import ndb

from __init__ import db, manager, auth_test
from flask.ext.login import UserMixin
import hashlib
import uuid
import datetime




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








