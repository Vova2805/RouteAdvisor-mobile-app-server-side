export class TitleIcon {
    private _title;
    private _icon;

    constructor(title, icon) {
        this._title = title;
        this._icon = icon;
    }


    get title() {
        return this._title;
    }

    set title(value) {
        this._title = value;
    }

    get icon() {
        return this._icon;
    }

    set icon(value) {
        this._icon = value;
    }
}