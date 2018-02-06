# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render, get_object_or_404
from django.http import HttpResponse
from .models import Employee, Dependent
from .forms import EmployeeForm, DependentForm


def create_employee(request):

    if request.method == 'POST':

        employee_form = EmployeeForm(request.POST, prefix='form1')
        dependent_form = DependentForm(request.POST, prefix='form2')

        if employee_form.is_valid() and dependent_form.is_valid():
            employee_first_name = employee_form.cleaned_data['first_name']
            employee_last_name = employee_form.cleaned_data['last_name']

            dependent_first_name = dependent_form.cleaned_data['first_name']
            dependent_last_name = dependent_form.cleaned_data['last_name']

            employee = employee_form.save(commit=False)
            employee.save()
            get_e = Employee.objects.get(pk=employee.id)

            dep = Dependent(first_name=dependent_first_name, last_name=dependent_last_name, employee_provider=get_e)
            dep.adjust_cost()
            dep.save()

            get_e.adjust_benefit_cost()
            e = get_e.benefit_cost
            Employee.objects.filter(pk=employee.id).update(benefit_cost=e)

            args = {'e_form': employee_form, 'e_first_name': employee_first_name, 'e_last_name': employee_last_name,
                    'd_first_name': dependent_first_name, 'd_last_name': dependent_last_name}

            return render(request, 'benefits/success.html', args)

        return render(request, 'benefits/success.html', {'e_form': employee_form, 'd_form': dependent_form})

    # if a GET (or any other method) we'll create a blank form
    else:
        employee_form = EmployeeForm(prefix='form1')
        dependent_form = DependentForm(prefix='form2')

    return render(request, 'benefits/index.html', {'employee_form': employee_form, 'dependent_form': dependent_form})


def success(request):
    return render(request, 'benefits/success.html')


def employees(request):
    employee_list = Employee.objects.order_by('last_name')
    context = {
        'employee_list': employee_list,
    }
    return render(request, 'benefits/employees.html', context)


def detail(request, employee_id):
    employee = get_object_or_404(Employee, pk=employee_id)
    return render(request, 'benefits/detail.html', {'employee': employee})


def edit(request, employee_id):
    response = 'You are editing'
    return HttpResponse(response % employee_id)
