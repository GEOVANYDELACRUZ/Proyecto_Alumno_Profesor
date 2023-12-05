<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json['img']=array();

	//if(true){)
	if(isset($_GET["curso"])&&isset($_GET["alumno"])&&isset($_GET["profesor"])){
		
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		if ($conexion->connect_error) {
		  die("Connection failed: " . $conexion->connect_error);
		}		
		echo "Connected successfully";
		echo '<br>';
		$Curso=$_GET['curso'];
		$Alumno=$_GET['alumno'];
		$Profesor=$_GET['profesor'];
		
		$consulta="select * from tblAlumno A 
		inner join tblAlumCurs AC on A.DNI=AC.DNI
		inner join tblCurso C on AC.IDCurso=C.IDCurso
		where A.DNI like '$Alumno' && C.curNombre like '$Curso'";
        $resultado=mysqli_query($conexion,$consulta);
		$resp=mysqli_fetch_array($resultado);
		if($resp){
			echo "Curso ya existente";
			echo '<br>';
		}else{
			$result="call insertar_alum_curso ('$Alumno','$Curso')";
			if (mysqli_query($conexion,$result)) {
				echo "New record created successfully; CURSO ALUMNO NEW";
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