<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json=array();

    if(isset($_GET["profesor"])){
        $conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
        $profesor=$_GET['profesor'];
        $consulta="select C.IDCurso,C.curNombre
		from tblProfesor P 
		INNER join tblCursProf CP on P.IDProfesor = CP.IDProfesor
		INNER join tblCurso C on CP.IDCurso=C.IDCurso where P.IDProfesor like '$profesor'";
        $resultado=mysqli_query($conexion,$consulta);
		
        if($resp=mysqli_fetch_array($resultado)){
		    $resultado=mysqli_query($conexion,$consulta);
            while($registro=mysqli_fetch_array($resultado)){
                $result["IDCurso"]=$registro['IDCurso'];
			    $result["curNombre"]=$registro['curNombre'];
			    $json['tblCurso'][]=$result;
                
           }
        }
        else{
			$result["IDCurso"]=-1;
			$result["curNombre"]='No registra';
			$json['tblCurso'][]=$result;
        }
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