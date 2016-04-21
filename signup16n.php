<?php
define('DB_HOST', '127.0.0.1');
define('DB_USER', 'test');
define('DB_PASS', '');
define('DB_NAME', 'test_db');

$link = mysql_connect(DB_HOST, DB_USER, DB_PASS);
$db = mysql_select_db(DB_NAME);


//mysql_query('SET NAMES cp1251');
mysql_query('SET NAMES utf8');

	
 function search ($query) {   
     $addString=""; 

	$query = trim($query); 
    $query = mysql_real_escape_string($query);
    $query = htmlspecialchars($query); 


 $n = "SELECT `col2` FROM `uniontable` where `col1` = '$query'";         
			         $result1 = mysql_query($n);
                     $row1 = mysql_fetch_assoc($result1);  				            
					 $addString=$row1['col2'];
         return $addString;
         }


	$fullname = $_GET['fullname'];  
    $search_result = search ($fullname); 


    echo $search_result; 

mysql_close($link);
?>