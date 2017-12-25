from django.conf.urls import url

from three import views

urlpatterns = [
    url(r'^login/$',views.login),
    url(r'^register/$',views.register),
    url(r'^reqhealth/$',views.reqhealth),
    url(r'^reqvote/$', views.reqrote),
    url(r'^setvote/$',views.setvote),

    url(r'^reqaccount/$',views.reqaccount),
    url(r'^sethealthscore/$',views.sethealthscore),
    url(r'^setstudyscore/$',views.setstudyscore),
]