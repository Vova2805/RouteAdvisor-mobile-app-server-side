import "./vendor.ts";
import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {Ng2Webstorage} from "ng2-webstorage";
import {RouteAdvisorSharedModule, UserRouteAccessService} from "./shared";
import {RouteAdvisorServerHomeModule} from "./home/home.module";
import {RouteAdvisorServerAdminModule} from "./admin/admin.module";
import {RouteAdvisorServerAccountModule} from "./account/account.module";
import {RouteAdvisorServerEntityModule} from "./entities/entity.module";
import {customHttpProvider} from "./blocks/interceptor/http.provider";
import {PaginationConfig} from "./blocks/config/uib-pagination.config";
import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from "./layouts";
import {RouteAdvisorVideoModule} from "./entities/video/video.module";
import {RouteAdvisorTemplateDayModule} from "./entities/template-day/template-day.module";

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({prefix: 'jhi', separator: '-'}),
        RouteAdvisorSharedModule,
        RouteAdvisorServerHomeModule,
        RouteAdvisorServerAdminModule,
        RouteAdvisorServerAccountModule,
        RouteAdvisorServerEntityModule,
        RouteAdvisorVideoModule,
        RouteAdvisorTemplateDayModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [JhiMainComponent]
})
export class RouteAdvisorServerAppModule {
}
