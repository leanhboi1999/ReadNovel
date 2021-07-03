<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Favorite extends Model
{
    protected $fillable = ['username', 'name', 'thumbal', 'chapter', 'url', 'view'];
}
