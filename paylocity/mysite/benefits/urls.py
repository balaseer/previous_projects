from django.conf.urls import url


from . import views

app_name = 'benefits'

urlpatterns = [
    url(r'^$', views.create_employee, name='index'),
    url(r'^(?P<employee_id>[0-9]+)/$', views.detail, name='detail'),
    url(r'^(?P<employee_id>[0-9]+)/edit/$', views.edit, name='edit'),
    url(r'^employees/$', views.employees, name='employees'),
    url(r'^success/$', views.success, name='success'),

]
