# Generated by Django 3.2.7 on 2021-10-13 13:22

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('crypto', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='currency',
            name='description',
            field=models.CharField(default='some description', max_length=1000),
        ),
        migrations.AddField(
            model_name='currency',
            name='supply',
            field=models.PositiveIntegerField(default=100000),
        ),
    ]