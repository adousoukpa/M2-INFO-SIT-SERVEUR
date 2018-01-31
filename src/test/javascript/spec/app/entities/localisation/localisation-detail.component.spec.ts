/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ServeurTestModule } from '../../../test.module';
import { LocalisationDetailComponent } from '../../../../../../main/webapp/app/entities/localisation/localisation-detail.component';
import { LocalisationService } from '../../../../../../main/webapp/app/entities/localisation/localisation.service';
import { Localisation } from '../../../../../../main/webapp/app/entities/localisation/localisation.model';

describe('Component Tests', () => {

    describe('Localisation Management Detail Component', () => {
        let comp: LocalisationDetailComponent;
        let fixture: ComponentFixture<LocalisationDetailComponent>;
        let service: LocalisationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServeurTestModule],
                declarations: [LocalisationDetailComponent],
                providers: [
                    LocalisationService
                ]
            })
            .overrideTemplate(LocalisationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocalisationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocalisationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Localisation('123')));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith('123');
                expect(comp.localisation).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
