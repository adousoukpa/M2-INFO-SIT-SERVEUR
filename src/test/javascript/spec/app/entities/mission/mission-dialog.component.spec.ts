/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServeurTestModule } from '../../../test.module';
import { MissionDialogComponent } from '../../../../../../main/webapp/app/entities/mission/mission-dialog.component';
import { MissionService } from '../../../../../../main/webapp/app/entities/mission/mission.service';
import { Mission } from '../../../../../../main/webapp/app/entities/mission/mission.model';

describe('Component Tests', () => {

    describe('Mission Management Dialog Component', () => {
        let comp: MissionDialogComponent;
        let fixture: ComponentFixture<MissionDialogComponent>;
        let service: MissionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServeurTestModule],
                declarations: [MissionDialogComponent],
                providers: [
                    MissionService
                ]
            })
            .overrideTemplate(MissionDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MissionDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MissionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Mission('123');
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.mission = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'missionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Mission();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.mission = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'missionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
