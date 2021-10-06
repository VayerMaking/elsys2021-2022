from django.db import models


class Currency(models.Model):
    name = models.CharField(max_length=50)
    price = models.FloatField()  # price in USD
    code = models.CharField(max_length=3)  # eg btc , eth