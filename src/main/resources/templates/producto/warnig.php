<?php
$nombre=$_POST['nombre'];
$p=$_POST['p'];
foreach($p as $r) {
if($nombre==$r ){
    echo $nombre." "."esta duplicado";}
    else{
        
        echo " ";
    }}
?>