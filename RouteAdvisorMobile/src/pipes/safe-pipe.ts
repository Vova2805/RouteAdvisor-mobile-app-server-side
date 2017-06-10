import {Pipe, PipeTransform} from "@angular/core";
import {DomSanitizer} from "@angular/platform-browser";

@Pipe({
    name: 'safe',
    pure: true
})
export class SafePipe implements PipeTransform {

    constructor(private sanitizer: DomSanitizer) {
    }

    transform(value: string, ...args) {
        return this.sanitizer.bypassSecurityTrustResourceUrl(value);
    }
}