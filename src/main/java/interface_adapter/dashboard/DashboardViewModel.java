package interface_adapter.dashboard;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Dashboard View.
 */
public class DashboardViewModel extends ViewModel<DashboardState> {
    public DashboardViewModel() {
        super("dashboard");
        setState(new DashboardState());
    }
}

