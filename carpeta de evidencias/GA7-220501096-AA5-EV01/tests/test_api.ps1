param()
Write-Host "Ejecutando pruebas PowerShell contra API de registro/login"
$api = 'http://localhost:8082/api'

# Generar email único
$ts = [int](Get-Date -UFormat %s)
$email = "test_$ts@example.com"
Write-Host "Usando email: $email"

Write-Host "`n1) Registro"
$reg = Invoke-RestMethod -Uri ($api + '/registro') -Method POST -ContentType 'application/json' -Body (@{username='tester'; email=$email; password='secret123'} | ConvertTo-Json)
Write-Host "Respuesta:" ($reg | ConvertTo-Json -Depth 2)

Write-Host "`n2) Login correcto"
$login = Invoke-RestMethod -Uri ($api + '/login') -Method POST -ContentType 'application/json' -Body (@{email=$email; password='secret123'} | ConvertTo-Json)
Write-Host "Respuesta:" ($login | ConvertTo-Json -Depth 2)

Write-Host "`n3) Login incorrecto (password equivocado)"
try {
  $bad = Invoke-RestMethod -Uri ($api + '/login') -Method POST -ContentType 'application/json' -Body (@{email=$email; password='wrongpass'} | ConvertTo-Json)
  Write-Host "Se esperaba error pero obtuvo:" ($bad | ConvertTo-Json -Depth 2)
} catch {
  Write-Host "Error esperado en autenticación incorrecta: $($_.Exception.Response.StatusCode.Value__)"
}

Write-Host "`nPruebas finalizadas."
