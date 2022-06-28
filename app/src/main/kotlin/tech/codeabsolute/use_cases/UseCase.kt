package tech.codeabsolute.use_cases

sealed interface UseCase {

    interface WithInputAndOutput<Input, Output> {
        operator fun invoke(input: Input): Output
    }

    interface WithOutput<Output> {
        operator fun invoke(): Output
    }

    interface WithInput<Input> {
        operator fun invoke(input: Input)
    }

    interface Default {
        operator fun invoke()
    }

    interface WithInputAndOutputSuspending<Input, Output> {
        suspend operator fun invoke(input: Input): Output
    }

    interface WithOutputSuspending<Output> {
        suspend operator fun invoke(): Output
    }

    interface WithInputSuspending<Input> {
        suspend operator fun invoke(input: Input)
    }

    interface DefaultSuspending {
        suspend operator fun invoke()
    }
}