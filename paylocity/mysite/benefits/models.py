# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models


def get_percentage(amount, percent):
    """
    A helper function that generates a percent value

    Usage: If I want 10% of 100 --> get_percentage(100, 10) --> this would return 10

    :param amount:
    :param percent:
    :return:
    """
    try:
        return_percent_amount = (amount * percent) / 100
        return return_percent_amount
    except:
        return 0


class Person(models.Model):
    """
    Abstract class that represents a person with a first and last name
    """
    first_name = models.CharField(max_length=20)
    last_name = models.CharField(max_length=20)

    class Meta:
        abstract = True


class Employee(Person):
    """
    A class that represents an employee and inherits from the abstract Person class
    """
    num_annual_paychecks = 26
    salary = (2000 * num_annual_paychecks)  # variable that holds default salary for an employee = 52000
    employee_yearly_benefit_cost = 1000  # variable that holds default benefit cost for a single employee
    cost_adjusted = False
    annual_salary = models.DecimalField(max_digits=12, decimal_places=2, default=salary)  # defining employee salary for DB
    benefit_cost = models.DecimalField(max_digits=12, decimal_places=2, default=employee_yearly_benefit_cost)  # defining employee benefit cost for DB

    def __str__(self):
        """
        Employee name getter
        :return:
        """
        return '{0} {1}'.format(self.first_name, self.last_name)

    def set_employee_name(self, first_name, last_name):
        """
        Employee name setter

        :param first_name:
        :param last_name:
        :return:
        """
        self.first_name = first_name
        self.last_name = last_name

    def has_dependents(self):
        """
        Boolean function that checks if an employee has dependents
        :return:
        """
        return self.dependents.exists()

    def get_dependents(self):
        """
        Returns all dependents tied to an employee
        :return:
        """
        return self.dependents.all()

    def get_dependent_count(self):
        return self.dependents.count()

    def has_discount(self):
        """
        Boolean function that checks if an employee is eligible for a discount
        :return:
        """
        return self.first_name[0] == 'A' or self.first_name[0] == 'a'

    def adjust_benefit_cost(self):
        """
        Adjusts employee benefit cost by taking dependents and discount into account
        :return:
        """
        if not self.cost_adjusted:
            if self.has_discount():
                discount_price = get_percentage(self.benefit_cost, 10)
                self.benefit_cost = (self.benefit_cost - discount_price)

            if self.has_dependents():
                for dependent in self.get_dependents():
                    self.benefit_cost += dependent.cost

            self.cost_adjusted = True

    def generate_salary_after_deductions(self):
        """
        Generates employee salary after benefit deductions
        :return:
        """
        return round(self.annual_salary - self.benefit_cost, 2)

    def generate_deductions_per_paycheck(self):
        """
        Generates the dollar amount of deductions per paycheck
        :return:
        """
        try:
            calculation = round(self.benefit_cost / 26, 2)
            return calculation
        except:
            return 0

    def generate_each_paycheck_after_deductions(self):
        """
        Generate the dollar amount of each paycheck after deductions
        :return:
        """
        try:
            calculation = (self.annual_salary - self.benefit_cost) / self.num_annual_paychecks
            return round(calculation, 2)
        except:
            return 0


class Dependent(Person):
    """
    Dependent class that inherits from the Person class
    """
    cost_adjusted = False
    dependent_yearly_benefit_cost = 500  # variable that holds default benefit cost for a single dependent
    employee_provider = models.ForeignKey(Employee, related_name='dependents')  # Foreign key that ties dependent objects to employee objects
    cost = models.DecimalField(max_digits=12, decimal_places=2, default=dependent_yearly_benefit_cost)  # defining employee benefit cost for DB

    wife = 'Wife'
    husband = 'Husband'
    child = 'Child'

    DEPENDENT_TYPE = (
        (wife, 'Wife'),
        (husband, 'Husband'),
        (child, 'Child'),
    )

    dependent_type = models.CharField(max_length=7, choices=DEPENDENT_TYPE)

    def __str__(self):
        """
        Dependent name getter
        :return:
        """
        return '{0} {1}'.format(self.first_name, self.last_name)

    def set_dependent_name(self, first_name, last_name):
        """
        Dependent name setter

        :param first_name:
        :param last_name:
        :return:
        """
        self.first_name = first_name
        self.last_name = last_name

    def has_discount(self):
        """
        Boolean function that check if the dependent is eligible for a discount
        :return:
        """
        return self.first_name[0] == 'A' or self.first_name[0] == 'a'

    def adjust_cost(self):
        """
        Generates the total annual cost for a single dependent
        :return:
        """
        if not self.cost_adjusted:
            if self.has_discount():
                discount_price = get_percentage(self.cost, 10)
                self.cost = (self.cost - discount_price)
            self.cost_adjusted = True



