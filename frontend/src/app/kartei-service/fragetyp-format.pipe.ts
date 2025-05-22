import {Injectable, Pipe, PipeTransform} from '@angular/core';
import {FrageTyp} from './domain';
@Injectable({
  providedIn: 'root'
})
@Pipe({
  standalone: true,
  name: 'fragetypFormat'
})
export class FragetypFormatPipe implements PipeTransform {
  transform(frageTyp : FrageTyp): string {
    switch (frageTyp) {
      case FrageTyp.NORMAL: {
        return 'Normal';

      }
      case FrageTyp.SINGLE_CHOICE: {
        return 'Single Choice';
      }
      case FrageTyp.MULTIPLE_CHOICE: {
        return 'Multiple Choice';
      }
      default: {
        return 'Unbekannter Fragetyp';
      }
    }
  }
}
