<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json=array();

	if(isset($_GET["Correo"])){
		
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		if ($conexion->connect_error) {
		  die("Connection failed: " . $conexion->connect_error);
		}		
        
        $Correo=$_GET['Correo'];

        $consulta="select obtenerIDProfesor('$Correo') AS cod";
        $resultado=mysqli_query($conexion,$consulta);
		if ($registro=mysqli_fetch_array($resultado)) { 
                $result["cod"]=$registro['cod'];
            } else {
                $result["cod"]=-1;
            }		
		$json['tblUser'][]=$result;
		mysqli_close($conexion);
		echo json_encode($json);
	}else{		
		echo '<br>';
		echo 'No ingreso ningun registro';
		echo '<br>';
	}
?>