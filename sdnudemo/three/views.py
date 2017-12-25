# -*- coding: utf-8 -*-
from __future__ import unicode_literals

import json
import random

from django.http import HttpResponse
from django.shortcuts import render

# Create your views here.
from three import models

# 登录请求
def login(request):
    username = request.GET.get("username", "0")
    password = request.GET.get("password", "0")

    # 找到用户名为username的用户
    try:
        thisuser = models.Users.objects.get(name=username)
    except:
        thisuser = None

    if thisuser is None:
        return HttpResponse(json.dumps({"type": "0", "content": "用户名不存在"}), content_type="application/json")

    if thisuser.password.__eq__(password):
        # type = 2 代表登录成功

        return HttpResponse(json.dumps({"type": "2", "content": "登录成功","usermsg":[{"sign":thisuser.sign},{"healthScore":thisuser.healthScore},{"studyScore":thisuser.studyScore}]}), content_type="application/json")
    else:
        # type = 1 代表密码错误
        return HttpResponse(json.dumps({"type": "1", "content": "密码错误"}), content_type="application/json")

# 注册请求
def register(request):
    username = request.GET.get("username", "0")
    password = request.GET.get("password", "0")
    try:
        thisuser = models.Users.objects.get(name=username)
    except:
        thisuser = None

    if thisuser is None:
        # type = 2 表示注册成功
        models.Users.objects.create(name=username, password=password,healthScore=0,studyScore=0)
        return HttpResponse(json.dumps({"type": "2", "content": "注册成功"}), content_type="application/json")
    else:
        # type = 1 表示用户已存在
        return HttpResponse(json.dumps({"type": "1", "content": "注册失败，该用户已存在"}), content_type="application/json")

#请求10条健康测试题
def reqhealth(request):
    #healthqts = models.Healthqt.objects.all()
    questions = {}
    data = []

    # 随机生成整数1～5
    num = random.randint(1,5)
    questions["group"] = num
    group = models.Healthqt.objects.filter(group=num)
    for member in group:
        list = {}
        list["content"] = member.content
        data.append(list)
    questions["questions"] = data
    return HttpResponse(json.dumps(questions),content_type="application/json")


# 请求一个投票问题（包含该投票问题）
def reqrote(request):
    voteid = request.GET.get("voteid")

    if voteid:
        vote = models.Votes.objects.get(id=voteid)
        content = vote.content
        agreenum = vote.agreeNum
        disagreenum = vote.disagreeNum
        # temp为rate的id
        return HttpResponse(
            json.dumps({"id": voteid, "content": content, "agreenum": agreenum, "disagreenum": disagreenum}),
            content_type="application/json")
    else:
        pass

# # 修改个性签名
# def setsign(request):
#     username = request.GET.get("username","0")
#     sign = request.GET.get("sign","0")
#     user = models.Users.objects.get(name = username)
#     user.sign = sign
#     user.save(update_fields = ["sign"])
#     return HttpResponse(json.dumps({"type":1}),content_type="application/json")

# 添加投票的支持数和反对数（支持或反对之后就不能修改了
# type == 1---->反对
# type == 2---->同意


# 修改vate
def setvote(request):
    username = request.GET.get("username","")
    voteid = request.GET.get("voteid",0)
    select = request.GET.get("select")

    vote = models.Votes.objects.get(id = voteid)
    try:
        uservote = models.UserVote.objects.get(voteid = int(voteid),username = str(username))
    except:
        uservote = None
    if uservote:
        return HttpResponse(json.dumps({"message":"你已经参与了该问卷！"}),content_type="application/json")
    else:
        models.UserVote.objects.create(voteid = voteid,username = username,select = select)
        vote = models.Votes.objects.get(id = voteid)
        if select:
            vote.agreeNum += 1
            vote.save()
        else:
            vote.disagreeNum += 1
            vote.save()
    return HttpResponse(json.dumps({"agreenum":vote.agreeNum,"disagreenum":vote.disagreeNum}),content_type="application/json")






# 每次刷新app页面或点开该页面后都会重新得到该投票的内容，同意数以及反对数
# def getvote(request):
#     id = request.GET.get("id",0)
#     vote = models.Votes.objects.get(id = id)
#     content = vote.content
#     agree = vote.agreeNum
#     disagree = vote.disagreeNum
#     return  HttpResponse(json.dumps({"content":content,"agreenum":agree,"disagreenum":disagree}),content_type="application/json")









# 当打开个人账户页面时，请求user获得个人信息
def reqaccount(request):
    name = request.GET.get("username","")

    user = models.Users.objects.get(name = name)
    return HttpResponse(json.dumps({"username":user.name,"sign":user.sign,"healthscore":user.healthScore,"studyscore":user.studyScore}),content_type="application/json")

# 设置healthscore的值
def sethealthscore(request):
    name = request.GET.get("name","0")
    score = request.GET.get("healthscore",0)
    user = models.Users.objects.get(name = name)
    user.healthScore = int(score)
    user.save()
    return HttpResponse(json.dumps({"type":1}),content_type="application/json")


def setstudyscore(request):
    name = request.GET.get("name","0")
    score = request.GET.get("studyscore",0)
    user = models.Users.objects.get(name = name)
    user.studyScore = int(score)
    user.save()
    return HttpResponse(json.dumps({"type":1}),content_type="application/json")





























