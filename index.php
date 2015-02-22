<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <META HTTP-EQUIV="Content-Language" Content="cs">
    <title>Sci-fi - download</title>
</head>
<body>
<?php

function printList($fileMask, $desc)
{
    echo "<h1>$desc</h1>";
    echo "<ul>";
    //path to directory to scan
    //get all image files with a .jpg extension
    $images = glob("*.zip");
    usort($images, create_function('$a,$b', 'return filemtime($b) - filemtime($a);'));
    //print each file name
    foreach ($images as $image) {
        if (substr_count($image, $fileMask) > 0) {
            $time = date("d.m.Y H:i:s.", filemtime($image));
            echo "<li><a href='$image'>$image</a> - nahráno v $time</li>";
        }
    }
    echo "</ul>";
}

printList("osetrovna", "Ošetřovna");
printList("opravy", "Opravy");
printList("lod", "Můstek/Strojovna");
?>
</body>
</html>