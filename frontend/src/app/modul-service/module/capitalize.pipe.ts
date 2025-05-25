import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  standalone: true,
  name: 'capitalize'
})
export class CapitalizePipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return '';
    value = value.toLowerCase();
    return value.charAt(0).toUpperCase() + value.slice(1);
  }
}
