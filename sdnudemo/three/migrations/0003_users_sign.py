# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-12-14 11:09
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('three', '0002_healthqt_group'),
    ]

    operations = [
        migrations.AddField(
            model_name='users',
            name='sign',
            field=models.TextField(null=True),
        ),
    ]