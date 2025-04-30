import { AbstractControl, ValidationErrors } from '@angular/forms';

export function passwordComplexityValidator(control: AbstractControl): ValidationErrors | null {
  const value: string = control.value || '';

  const errors: ValidationErrors = {};

  if (value.length < 8) {
    errors['minLength'] = 'Le mot de passe doit contenir au moins 8 caractères.';
  }
  if (!/[a-z]/.test(value)) {
    errors['lowercase'] = 'Le mot de passe doit contenir une lettre minuscule.';
  }
  if (!/[A-Z]/.test(value)) {
    errors['uppercase'] = 'Le mot de passe doit contenir une lettre majuscule.';
  }
  if (!/[0-9]/.test(value)) {
    errors['number'] = 'Le mot de passe doit contenir un chiffre.';
  }
  if (!/[^A-Za-z0-9]/.test(value)) {
    errors['special'] = 'Le mot de passe doit contenir un caractère spécial.';
  }

  return Object.keys(errors).length ? errors : null;
}
