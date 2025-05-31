import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'lernzeitFormat',
  standalone: true
})
export class LernzeitFormatPipe implements PipeTransform {
  transform(secondsLearned: number | undefined): string {
    if (secondsLearned === undefined || secondsLearned < 0) return '0s';

    const days = Math.floor(secondsLearned / (24 * 3600));
    if (days > 0) return `${days}d`;

    const hours = Math.floor(secondsLearned / 3600);
    if (hours > 0) return `${hours}h`;

    const minutes = Math.floor(secondsLearned / 60);
    if (minutes > 0) return `${minutes}m`;

    return `${secondsLearned % 60}s`;
  }
}
