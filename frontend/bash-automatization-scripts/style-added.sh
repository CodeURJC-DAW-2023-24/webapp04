#!/bin/bash

# Define los directorios
css_dir="../src/assets"
styles_file="../src/styles.css"

# Verifica si el archivo styles.css existe y créalo si no
if [ ! -f "$styles_file" ]; then
  touch "$styles_file"
fi

# Vacía el archivo styles.css
> "$styles_file"

# Encuentra todos los archivos .css y genera las declaraciones @import
find "$css_dir" -type f -name "*.css" | while read -r css_file; do
  relative_path=$(realpath --relative-to="$(dirname "$styles_file")" "$css_file")
  echo "@import '$relative_path';" >> "$styles_file"
done

echo "Se han añadido las importaciones de los archivos CSS al archivo styles.css."
