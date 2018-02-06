# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.test import TestCase
from django.urls import reverse

from .models import Employee, Dependent


class EmployeeModelTests(TestCase):

    def test_employee_dependents_with_dependent(self):
        e_first_name = "John"
        e_last_name = "Doe"

        d_first_name = "Jane"
        d_last_name= "Doe"
        d_type = 'Wife'

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.save()
        dependent = Dependent(first_name=d_first_name, last_name=d_last_name, employee_provider=employee, dependent_type=d_type)
        dependent.save()
        self.assertIs(employee.has_dependents(), True)

    def test_employee_dependents_without_dependents(self):
        e_first_name = "John"
        e_last_name = "Doe"

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.save()
        self.assertIs(employee.has_dependents(), False)

    def test_employee_has_discount_without_discount(self):
        e_first_name = "John"
        e_last_name = "Doe"

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.save()
        self.assertIs(employee.has_discount(), False)

    def test_employee_has_discount_with_discount(self):
        e_first_name = "Alex"
        e_last_name = "Johnson"

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.save()
        self.assertIs(employee.has_discount(), True)

    def test_cost_has_been_adjusted(self):
        e_first_name = "John"
        e_last_name = "Doe"

        d_first_name = "Jane"
        d_last_name = "Doe"
        d_type = 'Wife'

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.save()

        get_e = Employee.objects.get(pk=employee.id)

        dependent = Dependent(first_name=d_first_name, last_name=d_last_name, employee_provider=employee,
                              dependent_type=d_type)
        dependent.adjust_cost()
        dependent.save()

        get_e.adjust_benefit_cost()
        e = get_e.benefit_cost
        Employee.objects.filter(pk=employee.id).update(benefit_cost=e)

        dependent.save()
        self.assertIs(get_e.cost_adjusted, True)

    def test_benefit_cost_no_discount_no_dependents(self):
        e_first_name = "John"
        e_last_name = "Doe"

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.adjust_benefit_cost()
        employee.save()

        self.assertEqual(employee.benefit_cost, 1000)

    def test_benefit_cost_with_discount_no_dependents(self):
        e_first_name = "Alex"
        e_last_name = "Johnson"

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.adjust_benefit_cost()
        employee.save()

        self.assertEqual(employee.benefit_cost, 900)

    def test_benefit_cost_no_discount_with_dependent(self):
        e_first_name = "John"
        e_last_name = "Doe"

        d_first_name = "Jane"
        d_last_name = "Doe"
        d_type = 'Wife'

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.save()

        get_e = Employee.objects.get(pk=employee.id)

        dependent = Dependent(first_name=d_first_name, last_name=d_last_name, employee_provider=employee,
                              dependent_type=d_type)
        dependent.adjust_cost()
        dependent.save()

        get_e.adjust_benefit_cost()
        e = get_e.benefit_cost
        Employee.objects.filter(pk=employee.id).update(benefit_cost=e)

        dependent.save()

        self.assertEqual(get_e.benefit_cost, 1500)

    def test_benefit_cost_with_discount_with_dependent(self):
        e_first_name = "Alex"
        e_last_name = "Johnson"

        d_first_name = "Jane"
        d_last_name = "Doe"
        d_type = 'Wife'

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.save()

        get_e = Employee.objects.get(pk=employee.id)

        dependent = Dependent(first_name=d_first_name, last_name=d_last_name, employee_provider=employee,
                              dependent_type=d_type)
        dependent.adjust_cost()
        dependent.save()

        get_e.adjust_benefit_cost()
        e = get_e.benefit_cost
        Employee.objects.filter(pk=employee.id).update(benefit_cost=e)

        dependent.save()

        self.assertEqual(get_e.benefit_cost, 1400)

    def test_benefit_cost_with_discount_with_dependent_discount(self):
        e_first_name = "Alex"
        e_last_name = "Johnson"

        d_first_name = "Alex"
        d_last_name = "Doe"
        d_type = 'Wife'

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.save()

        get_e = Employee.objects.get(pk=employee.id)

        dependent = Dependent(first_name=d_first_name, last_name=d_last_name, employee_provider=employee,
                              dependent_type=d_type)
        dependent.adjust_cost()
        dependent.save()

        get_e.adjust_benefit_cost()
        e = get_e.benefit_cost
        Employee.objects.filter(pk=employee.id).update(benefit_cost=e)

        dependent.save()

        self.assertEqual(get_e.benefit_cost, 1350)

    def test_salary_after_deduction_employee_no_discount_no_dependent(self):
        e_first_name = "John"
        e_last_name = "Doe"

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.save()

        self.assertEqual(employee.generate_salary_after_deductions(), 51000)

    def test_salary_after_deduction_employee_no_discount_with_dependent(self):
        e_first_name = "John"
        e_last_name = "Johnson"

        d_first_name = "Jane"
        d_last_name = "Doe"
        d_type = 'Wife'

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.save()

        get_e = Employee.objects.get(pk=employee.id)

        dependent = Dependent(first_name=d_first_name, last_name=d_last_name, employee_provider=employee,
                              dependent_type=d_type)
        dependent.adjust_cost()
        dependent.save()

        get_e.adjust_benefit_cost()
        e = get_e.benefit_cost
        Employee.objects.filter(pk=employee.id).update(benefit_cost=e)

        dependent.save()

        self.assertEqual(get_e.generate_salary_after_deductions(), 50500)

    def test_generate_deductions_per_paycheck(self):
        e_first_name = "John"
        e_last_name = "Johnson"

        d_first_name = "Jane"
        d_last_name = "Doe"
        d_type = 'Wife'

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.save()

        get_e = Employee.objects.get(pk=employee.id)

        dependent = Dependent(first_name=d_first_name, last_name=d_last_name, employee_provider=employee,
                              dependent_type=d_type)
        dependent.adjust_cost()
        dependent.save()

        get_e.adjust_benefit_cost()
        e = get_e.benefit_cost
        Employee.objects.filter(pk=employee.id).update(benefit_cost=e)

        dependent.save()

        self.assertEqual(get_e.generate_deductions_per_paycheck(), 57.69)

    def test_generate_each_paycheck_after_deductions(self):
        e_first_name = "John"
        e_last_name = "Johnson"

        d_first_name = "Jane"
        d_last_name = "Doe"
        d_type = 'Wife'

        employee = Employee(first_name=e_first_name, last_name=e_last_name)
        employee.save()

        get_e = Employee.objects.get(pk=employee.id)

        dependent = Dependent(first_name=d_first_name, last_name=d_last_name, employee_provider=employee,
                              dependent_type=d_type)
        dependent.adjust_cost()
        dependent.save()

        get_e.adjust_benefit_cost()
        e = get_e.benefit_cost
        Employee.objects.filter(pk=employee.id).update(benefit_cost=e)

        dependent.save()

        self.assertEqual(get_e.generate_each_paycheck_after_deductions(), 1942.31)

# TODO: TEST ALL VIEW FUNCTIONALITY
