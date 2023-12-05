<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json['img']=array();

	//if(true){)
	if(isset($_GET["codA"])&&isset($_GET["codC"])&&isset($_GET["crit"])){
		
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		if ($conexion->connect_error) {
			die("Connection failed: " . $conexion->connect_error);
		  }
		
		$codA=$_GET['codA'];
		$codC=$_GET['codC'];
		$crit=$_GET['crit'];
		echo "Connected successfully";
		
		$consulta="call insertar_nota_alum_curso('$codA','$codC','$crit');";
		if (mysqli_query($conexion,$consulta)) {
			echo "New record created successfully; NOTA NEW";
			echo '<br>';
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