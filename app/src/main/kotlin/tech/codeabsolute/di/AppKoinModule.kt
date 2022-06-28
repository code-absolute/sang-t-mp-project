package tech.codeabsolute.di

import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import tech.codeabsolute.AppViewModel
import tech.codeabsolute.client.QuickbooksOAuth2Client
import tech.codeabsolute.data.QuickbooksDataSource
import tech.codeabsolute.data.local.AppDatabase
import tech.codeabsolute.data.local.AppDatabaseImpl
import tech.codeabsolute.data.local.dao.ClientsDao
import tech.codeabsolute.data.local.dao.ClientsDaoImpl
import tech.codeabsolute.datasource.AccountsDataSource
import tech.codeabsolute.datasource.AccountsDataSourceImpl
import tech.codeabsolute.encryption.DataEncryption
import tech.codeabsolute.encryption.DataEncryptionImpl
import tech.codeabsolute.presentation.authentication.AuthenticationViewModel
import tech.codeabsolute.presentation.clients.ClientsSectionViewModel
import tech.codeabsolute.presentation.home.HomeViewModel
import tech.codeabsolute.presentation.login.LoginViewModel
import tech.codeabsolute.presentation.main_menu.MainMenuViewModel
import tech.codeabsolute.presentation.requisitions_table.RequisitionsTableViewModel
import tech.codeabsolute.presentation.requisitions_table.add_requisition_dialog.AddRequisitionDialogViewModel
import tech.codeabsolute.repository.AuthenticationRepository
import tech.codeabsolute.repository.AuthenticationRepositoryImpl
import tech.codeabsolute.repository.ClientsRepository
import tech.codeabsolute.repository.ClientsRepositoryImpl
import tech.codeabsolute.use_cases.authenticate_local_user.AuthenticateLocalUser
import tech.codeabsolute.use_cases.authenticate_local_user.AuthenticateLocalUserUseCase
import tech.codeabsolute.use_cases.authenticate_user.AuthenticateQuickbooksUser
import tech.codeabsolute.use_cases.authenticate_user.AuthenticateQuickbooksUserUseCase
import tech.codeabsolute.use_cases.create_client.GetRequisitionTypesUseCase
import tech.codeabsolute.use_cases.get_authentication_url.GetQuickbooksAuthenticationRequestUrl
import tech.codeabsolute.use_cases.get_authentication_url.GetQuickbooksAuthenticationRequestUrlUseCase
import tech.codeabsolute.use_cases.get_client.GetClient
import tech.codeabsolute.use_cases.get_client.GetClientUseCase
import tech.codeabsolute.use_cases.get_clients.GetClients
import tech.codeabsolute.use_cases.get_clients.GetClientsUseCase
import tech.codeabsolute.use_cases.set_access_tokens.SetQuickbooksAccessTokens
import tech.codeabsolute.use_cases.set_access_tokens.SetQuickbooksAccessTokensUseCase
import tech.codeabsolute.util.URIUtils

@Module
class AppKoinModule : AppModule {

    @Single
    fun provideMainViewModel(
        getQuickbooksAuthenticationRequestUrlUseCase: GetQuickbooksAuthenticationRequestUrlUseCase,
        authenticateQuickbooksUserUseCase: AuthenticateQuickbooksUserUseCase,
        setQuickbooksAccessTokensUseCase: SetQuickbooksAccessTokensUseCase
    ): AuthenticationViewModel =
        AuthenticationViewModel(
            getQuickbooksAuthenticationRequestUrlUseCase = getQuickbooksAuthenticationRequestUrlUseCase,
            authenticateQuickbooksUserUseCase = authenticateQuickbooksUserUseCase,
            setQuickbooksAccessTokensUseCase = setQuickbooksAccessTokensUseCase
        )

    @Single
    fun provideAuthenticateUserUseCase(quickbooksOAuth2Client: QuickbooksOAuth2Client): AuthenticateQuickbooksUserUseCase =
        AuthenticateQuickbooksUser(quickbooksOAuth2Client)

    @Single
    fun provideGetAuthenticationUrlUseCase(quickbooksOAuth2Client: QuickbooksOAuth2Client): GetQuickbooksAuthenticationRequestUrlUseCase =
        GetQuickbooksAuthenticationRequestUrl(quickbooksOAuth2Client)

    @Single
    fun provideSetAccessTokensUseCaseUseCase(quickbooksOAuth2Client: QuickbooksOAuth2Client): SetQuickbooksAccessTokensUseCase =
        SetQuickbooksAccessTokens(quickbooksOAuth2Client)

    @Single
    fun provideURIUtils(): URIUtils = URIUtils()

    @Single
    fun provideLoginViewModel(authenticateLocalUser: AuthenticateLocalUserUseCase): LoginViewModel =
        LoginViewModel(authenticateLocalUser)

    @Single
    fun provideAuthenticateLocalUserUseCase(authenticationRepository: AuthenticationRepository): AuthenticateLocalUserUseCase =
        AuthenticateLocalUser(authenticationRepository)

    @Single
    fun provideAuthenticationRepository(accountsDataSource: AccountsDataSource): AuthenticationRepository =
        AuthenticationRepositoryImpl(accountsDataSource)

    @Single
    fun provideAccountsDataSource(appDatabase: AppDatabase, dataEncryption: DataEncryption): AccountsDataSource =
        AccountsDataSourceImpl(appDatabase, dataEncryption)

    @Single
    fun provideAppViewModel(appDatabase: AppDatabase): AppViewModel = AppViewModel(appDatabase)

    @Single
    fun provideAppDatabase(dataEncryption: DataEncryption): AppDatabase = AppDatabaseImpl(dataEncryption)

    @Single
    fun provideDataEncryption(): DataEncryption = DataEncryptionImpl()

    @Single
    fun provideMainMenuViewModel(): MainMenuViewModel = MainMenuViewModel()

    @Single
    fun provideHomeViewModel(): HomeViewModel = HomeViewModel()

    @Single
    fun provideClientsSectionViewModel(getClientsUseCase: GetClientsUseCase): ClientsSectionViewModel =
        ClientsSectionViewModel(getClientsUseCase)

    @Single
    fun provideGetClientsUseCase(clientsRepository: ClientsRepository): GetClientsUseCase =
        GetClients(clientsRepository)

    @Single
    fun provideClientsRepository(
        clientsDao: ClientsDao,
        quickbooksDataSource: QuickbooksDataSource
    ): ClientsRepository = ClientsRepositoryImpl(clientsDao, quickbooksDataSource)

    @Single
    fun provideClientsDao(appDatabase: AppDatabase): ClientsDao = ClientsDaoImpl(appDatabase)

    @Single
    fun provideGetClientUseCase(clientsRepository: ClientsRepository): GetClientUseCase =
        GetClient(clientsRepository)

    @Single
    fun provideAddRequisitionDialogViewModel(getRequisitionTypesUseCase: GetRequisitionTypesUseCase): AddRequisitionDialogViewModel =
        AddRequisitionDialogViewModel(getRequisitionTypesUseCase)

    @Single
    fun provideRequisitionsTableViewModel(): RequisitionsTableViewModel =
        RequisitionsTableViewModel()
}