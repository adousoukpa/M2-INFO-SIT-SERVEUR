/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ServeurTestModule } from '../../../test.module';
import { LocalisationComponent } from '../../../../../../main/webapp/app/entities/localisation/localisation.component';
import { LocalisationService } from '../../../../../../main/webapp/app/entities/localisation/localisation.service';
import { Localisation } from '../../../../../../main/webapp/app/entities/localisation/localisation.model';

describe('Component Tests', () => {

    describe('Localisation Management Component', () => {
        let comp: LocalisationComponent;
        let fixture: ComponentFixture<LocalisationComponent>;
        let service: LocalisationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServeurTestModule],
                declarations: [LocalisationComponent],
                providers: [
                    LocalisationService
                ]
            })
            .overrideTemplate(LocalisationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocalisationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocalisationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Localisation('123')],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.localisations[0]).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
