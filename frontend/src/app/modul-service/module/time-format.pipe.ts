import {Injectable, Pipe, PipeTransform} from '@angular/core';
@Injectable({
  providedIn: 'root'
})
@Pipe({
  standalone: true,
  name: 'timeFormat'
})
export class TimeFormatPipe implements PipeTransform {
  transform(secondsLearned: number | undefined): string {
    if(secondsLearned) {
      const days = Math.floor(secondsLearned! / (24 * 3600));
      secondsLearned %= (24 * 3600);
      const hours = Math.floor(secondsLearned! / 3600);
      secondsLearned %= 3600;
      const minutes = Math.floor(secondsLearned! / 60);
      const remainingSeconds = secondsLearned! % 60;

      const parts: string[] = [];

      if (days > 0) parts.push(`${days}d`);
      if (hours > 0 || days > 0) parts.push(`${hours}h`);
      if (minutes > 0 || hours > 0 || days > 0) parts.push(`${minutes}m`);
      parts.push(`${remainingSeconds}s`);

      return parts.join(' ');
    } else {
      return '...'
    }
  }
}
