from django import forms

from benefits.models import Employee, Dependent


class EmployeeForm(forms.ModelForm):
    """
    Class that represents an employee form for user input
    """
    first_name = forms.CharField(max_length=20)
    last_name = forms.CharField(max_length=20)

    class Meta:
        model = Employee
        fields = ['first_name', 'last_name']


class DependentForm(forms.ModelForm):
    """
    Class that represents a dependent form for user input
    """
    first_name = forms.CharField(max_length=20)
    last_name = forms.CharField(max_length=20)

    class Meta:
        model = Dependent
        fields = ['first_name', 'last_name', 'dependent_type']


