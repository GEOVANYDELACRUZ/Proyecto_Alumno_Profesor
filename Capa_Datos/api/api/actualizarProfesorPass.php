<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json=array();

	//if(true){)
	if(isset($_GET["cod"]) && isset($_GET["pass"])){
		
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		if ($conexion->connect_error) {
		  die("Connection failed: " . $conexion->connect_error);
		}
		$Cod=$_GET['cod'];
		$Pass=$_GET['pass'];
		$consulta="call actualizarProfesorPass ( $Cod,'$Pass')";
		if (mysqli_query($conexion,$consulta)) {
            $msj = 1;  
        } else {
            $msj = 0;
        }
		mysqli_close($conexion);
        $json['msj'] = $msj;
        echo json_encode($json);
	}else{
		
		echo '<br>';
		echo 'No ingreso ningun registro';
		echo '<br>';
	}
?>