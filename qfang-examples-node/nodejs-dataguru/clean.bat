@echo off
@title delete if exist target

if exist target (
  rd /s /q target
) else (
  echo "not exist dir target"
)
