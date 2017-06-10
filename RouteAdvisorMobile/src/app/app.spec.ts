import {TestBed, ComponentFixture, async} from "@angular/core/testing";
import {IonicModule} from "ionic-angular";
import {AppComponent} from "./app.component";

let comp: AppComponent;
let fixture: ComponentFixture<AppComponent>;

describe('Component: Root Component', () => {

    beforeEach(async(() => {

        TestBed.configureTestingModule({

            declarations: [AppComponent],

            providers: [],

            imports: [
                IonicModule.forRoot(AppComponent)
            ]

        }).compileComponents();

    }));

    beforeEach(() => {

        fixture = TestBed.createComponent(AppComponent);
        comp = fixture.componentInstance;

    });

    afterEach(() => {
        fixture.destroy();
        comp = null;
    });

});