function onDeletePhone(id) {
  const form = document.getElementById('deleteForm' + id);
  const input = document.createElement('input');
  input.setAttribute('type', 'hidden');
  input.setAttribute('name', 'phoneId');
  input.setAttribute('value', id);
  form.appendChild(input);
  form.submit();
}