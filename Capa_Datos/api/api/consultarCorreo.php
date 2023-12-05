<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json=array();

    if(isset($_GET["correo"])){
        $conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
        $Correo=$_GET['correo'];
        $consulta="select obtenerIDProfesor('$Correo') as cod;";
        $resultado=mysqli_query($conexion,$consulta);
		if ($registro=mysqli_fetch_array($resultado)) { 
                $result["idProf"]=$registro['cod'];
            } else {
                $result["idProf"]=-1;
            }		
		$json['tblProf'][]=$result;
		mysqli_close($conexion);
		echo json_encode($json);
    }
    else{
            $resultar["IDCurso"]=-1;
            $resultar["curNombre"]='WS no retorna';
            $json['tblCurso'][]=$resultar;
            echo json_encode($json);
      }
?>