import os
import shutil
import stat
import sys
import subprocess
from pathlib import Path

def choisir_repertoire_installation():
	print("Recherche d'un dossier pour l'installation ...")
	options =  ["/usr/local/bin", str(Path.home() / ".local/bin")]

	for opt in options:
		if os.access(opt, os.W_OK):
			print(f"Installation possible dans : {opt}")
			return opt

	print("Aucun dossier accessible pour l'installation. (Relance avec sudo?)")
	sys.exit(1)

def rendre_executable(chemin_script):
	try:
		subprocess.run(["chmod", "+x", str(chemin_script)], check = True)
	except subprocess.CalledProcessError:
		print("Impossible de rendre le cript executable. (Relance avec sudo.)")

def installer_genmdp(classpath, output_dir):
	print("Création du script ...")

	chemin_script = Path(output_dir) / "genmdp"
	with open(chemin_script, "w") as f:
		f.write("#!/bin/bash\n")
		f.write(f'java -cp "{classpath}" GenMdp "$@"\n')

	rendre_executable(chemin_script)
	print(f"Script installé à : {chemin_script}")

def main():
	print("=== Instalation de genmdp ===")

	chemin_classes = input("Chemin vers le dossier contenant GenMdp.class et GenerateurDeMdp.class : ").strip()

	if not Path(chemin_classes).exists():
		print("Le chemin n'existe pas.")
		return

	dossier_install = choisir_repertoire_installation()
	installer_genmdp(chemin_classes, dossier_install)

if __name__ == "__main__":
	main()
