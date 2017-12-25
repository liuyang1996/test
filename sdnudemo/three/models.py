# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.

class Users(models.Model):
    # name为主键
    name = models.CharField(primary_key=True,max_length=32,null=False)
    password = models.CharField(null=False,max_length=32)
    healthScore = models.IntegerField()
    studyScore = models.IntegerField()
    # 个人签名
    sign = models.TextField(null=True)
    #设置在admin页面上每一个Article object显示的文字
    def __unicode__(self):
        return self.name

class Healthqt(models.Model):
    id = models.AutoField(primary_key=True)
    content = models.TextField(null=True)
    group = models.IntegerField(null=True)

    def __unicode__(self):
        return self.content


class Studyqt(models.Model):
    id = models.AutoField(primary_key=True)
    content = models.TextField(null=True)
    def __unicode__(self):
        return self.content

class Votes(models.Model):
    id = models.AutoField(primary_key=True)
    content = models.TextField(null=True)
    agreeNum = models.IntegerField()
    disagreeNum = models.IntegerField()
    def __unicode__(self):
        return self.content


class UserVote(models.Model):
    voteid = models.IntegerField(null=False)
    username = models.CharField(null=False,max_length=32)
    select = models.IntegerField(null=False)








