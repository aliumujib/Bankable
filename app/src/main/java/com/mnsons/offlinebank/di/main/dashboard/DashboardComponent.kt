package com.mnsons.offlinebank.di.main.dashboard

import com.mnsons.offlinebank.di.main.MainComponent
import com.mnsons.offlinebank.di.scopes.FragmentScope
import com.mnsons.offlinebank.ui.main.dashboard.DashboardFragment
import dagger.Component

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [DashboardModule].
 *
 * @see Component
 */
@FragmentScope
@Component(
    modules = [DashboardModule::class],
    dependencies = [MainComponent::class])
interface DashboardComponent {

    fun inject(dashboardFragment: DashboardFragment)

}
