from django.shortcuts import render
from django.http import HttpResponse
from .models import Currency


def index(request):
    # return HttpResponse("Hello, world. You're at the polls index.")
    return render(request, "index.html")


def trending(request):
    trending = Currency.objects.order_by('-price')[0]
    context = {
        "currency_name": trending.name,
        "currency_price": str(trending)
    }
    return render(request, "trending.html", context)
