from django.contrib import admin

from crypto.models import Currency

from .models import Currency
# Register your models here.

admin.site.register(Currency)
