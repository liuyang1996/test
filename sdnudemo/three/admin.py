# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib import admin

# Register your models here.
from three import models

admin.site.register(models.Users)

admin.site.register(models.Healthqt)

admin.site.register(models.Studyqt)

admin.site.register(models.Votes)

admin.site.register(models.UserVote)