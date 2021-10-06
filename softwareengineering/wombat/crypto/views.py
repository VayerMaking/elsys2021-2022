from django.shortcuts import render
from django.http import HttpResponse
from .models import Currency


def index(request):
    return HttpResponse("Hello, world. You're at the polls index.")


def trending(request):
    trending = Currency.objects.order_by('-price')[0]
    return HttpResponse("The currently trending currency is: " + str(trending))
