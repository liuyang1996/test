# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-12-14 09:10
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('three', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='healthqt',
            name='group',
            field=models.IntegerField(null=True),
        ),
    ]
