/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ServeurTestModule } from '../../../test.module';
import { MissionDetailComponent } from '../../../../../../main/webapp/app/entities/mission/mission-detail.component';
import { MissionService } from '../../../../../../main/webapp/app/entities/mission/mission.service';
import { Mission } from '../../../../../../main/webapp/app/entities/mission/mission.model';

describe('Component Tests', () => {

    describe('Mission Management Detail Component', () => {
        let comp: MissionDetailComponent;
        let fixture: ComponentFixture<MissionDetailComponent>;
        let service: MissionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServeurTestModule],
                declarations: [MissionDetailComponent],
                providers: [
                    MissionService
                ]
            })
            .overrideTemplate(MissionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MissionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MissionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Mission('123')));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith('123');
                expect(comp.mission).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
