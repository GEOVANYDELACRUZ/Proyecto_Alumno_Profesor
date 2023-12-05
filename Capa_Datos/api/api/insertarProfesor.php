<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json['img']=array();

	//if(true){)
	if(isset($_GET["Nombres"]) &&isset($_GET["Apellidos"]) &&isset($_GET["Cuenta"])&&isset($_GET["Pass"])){
		
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		if ($conexion->connect_error) {
		  die("Connection failed: " . $conexion->connect_error);
		}
		
		echo "Connected successfully";
		echo '<br>';
		$Nombres=$_GET['Nombres'];
        $Apellidos=$_GET['Apellidos'];
		$Cuenta=$_GET['Cuenta'];
        $Pass=$_GET['Pass'];
		echo "Nombres = ";
		echo "$Nombres";
		echo '<br>';
		echo "Apellidos = ";
		echo "$Apellidos";
		echo '<br>';
		echo "Cuenta = ";
		echo "$Cuenta";
		echo '<br>';
		echo "Pass = ";
		echo "$Pass";
		echo '<br>';
		$consulta="call insertar_profesor ( '$Nombres' , '$Apellidos' , '$Cuenta' , '$Pass' )";
		if (mysqli_query($conexion,$consulta)) {
			  echo "New record created successfully";
		} else {
			  echo "Error: " . $sql . "<br>" . mysqli_error($conexion);
		}
		mysqli_close($conexion);
	}else{
		
		echo '<br>';
		echo 'No ingreso ningun registro';
		echo '<br>';
	}
?>