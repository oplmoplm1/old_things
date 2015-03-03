"""
views.py

URL route handlers

Note that any handler params must match the URL route params.
For example the *say_hello* handler, handling the URL route '/hello/<username>',
  must be passed *username* as the argument.

"""

from flask import request, render_template, flash, url_for, redirect

from flask_cache import Cache
from flask.ext.mail import Message
from application.__init__ import app, mail
from decorators import login_required, admin_required




# Flask-Cache (configured to use App Engine Memcache API)
cache = Cache(app)




def say_hello(username):
    """Contrived example to demonstrate Flask's url routing capabilities"""
    return 'Hello %s' % username


def send_mail():
    msg = Message('Test', sender=app.config['ADMINS'][0], recipients=["860627972@qq.com"])
    msg.body = 'text body'
    msg.html = '<b>html</b> body'
    mail.send(msg)




@admin_required
def admin_only():
    """This view requires an admin account"""
    return 'Super-seekrit admin page.'



def warmup():
    """App Engine warmup handler
    See http://code.google.com/appengine/docs/python/config/appconfig.html#Warming_Requests

    """
    return ''

