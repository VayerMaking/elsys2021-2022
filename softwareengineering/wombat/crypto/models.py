from django.db import models


class Currency(models.Model):
    name = models.CharField(max_length=50)
    price = models.FloatField()  # price in USD
    code = models.CharField(max_length=3)  # eg btc , eth
    supply = models.PositiveIntegerField(default=100000)
    description = models.CharField(max_length=1000, default="some description")

    def __str__(self) -> str:
        return self.code + " " + str(self.price)
