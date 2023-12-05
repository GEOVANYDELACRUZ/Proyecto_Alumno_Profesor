<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json['img']=array();

	//if(true){)
	if(isset($_GET["dni"]) &&isset($_GET["nombres"])  &&isset($_GET["apaterno"]) &&isset($_GET["amaterno"])){
		
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		if ($conexion->connect_error) {
		  die("Connection failed: " . $conexion->connect_error);
		}		
		echo "Connected successfully";
		echo '<br>';

		$DNI=$_GET['dni'];
		$Nombres=$_GET['nombres'];
        $aPaterno=$_GET['apaterno'];
        $aMaterno=$_GET['amaterno'];

		$consulta="select dni from tblAlumno where dni like '$DNI'";
        $resultado=mysqli_query($conexion,$consulta);
		if($resp=mysqli_fetch_array($resultado)){
			echo "Alumno ya existente";
			echo '<br>';
		}
		else{
			echo "dni = ";
			echo "$DNI";
			echo '<br>';
			echo "Nombres = ";
			echo "$Nombres";
			echo '<br>';
			echo "apaterno = ";
			echo "$aPaterno";
			echo '<br>';
			echo "amaterno = ";
			echo "$aMaterno";
			echo '<br>';
			$consulta="call insertar_alumno ( '$DNI' , '$Nombres' , '$aPaterno' , '$aMaterno' )";
			if (mysqli_query($conexion,$consulta)) {
				echo "New record created successfully";
			} else {
				echo "Error: " . $sql . "<br>" . mysqli_error($conexion);
			}
		}
		mysqli_close($conexion);
	}else{
		
		echo '<br>';
		echo 'No ingreso ningun registro';
		echo '<br>';
	}
?>