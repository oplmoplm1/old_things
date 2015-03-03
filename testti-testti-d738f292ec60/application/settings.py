"""
settings.py

Configuration for Flask app

Important: Place your keys in the secret_keys.py module, 
           which should be kept out of version control.

"""

import os

from secret_keys import CSRF_SECRET_KEY, SESSION_KEY


class Config(object):
    MAIL_SERVER = 'smtp.googlemail.com'
    MAIL_PORT = 465
    MAIL_USE_TLS = False
    MAIL_USE_SSL = True
    MAIL_USERNAME = 'testti.noreply'
    MAIL_PASSWORD = 'zx1478520'
    MAIL_USE_APPENGINE = True
    # administrator list
    ADMINS = ['testti.noreply@gmail.com']
    # Set secret keys for CSRF protection
    SECRET_KEY = CSRF_SECRET_KEY
    CSRF_SESSION_KEY = SESSION_KEY
    SQLALCHEMY_DATABASE_URI = 'mysql+gaerdbms:///testtiweb?instance=testtiweb:datas'
    # Flask-Cache settings
    CACHE_TYPE = 'gaememcached'


class Development(Config):
    DEBUG = True
    # Flask-DebugToolbar settings
    DEBUG_TB_PROFILER_ENABLED = True
    DEBUG_TB_INTERCEPT_REDIRECTS = False
    CSRF_ENABLED = True

class Testing(Config):
    TESTING = True
    DEBUG = True
    CSRF_ENABLED = True

class Production(Config):
    DEBUG = False
    CSRF_ENABLED = True