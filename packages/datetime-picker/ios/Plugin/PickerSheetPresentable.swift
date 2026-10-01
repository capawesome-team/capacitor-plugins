import UIKit

@objc protocol PickerSheetPresentable: UIAdaptivePresentationControllerDelegate {
    func onCancelButton(sender: UIButton)
    func onDoneButton(sender: UIButton)
}

@available(iOS 26, *)
extension PickerSheetPresentable where Self: UIViewController {
    func setUpPickerSheet(picker: UIView, cancelText: String?, doneText: String, theme: Theme) {
        let contentHeight = addPickerSheetContent(picker: picker, cancelText: cancelText, doneText: doneText)
        sheetPresentationController?.detents = [.custom { context in
            min(contentHeight, context.maximumDetentValue)
        }]
        presentationController?.delegate = self
        if theme != .auto {
            presentationController?.traitOverrides.userInterfaceStyle = theme.userInterfaceStyle
        }
    }

    private func addPickerSheetContent(picker: UIView, cancelText: String?, doneText: String) -> CGFloat {
        let padding: CGFloat = 16
        let cancelButton = createPickerSheetButton(title: cancelText, configuration: .glass(), action: #selector(onCancelButton))
        let doneButton = createPickerSheetButton(title: doneText, configuration: .prominentGlass(), action: #selector(onDoneButton))
        cancelButton.isHidden = cancelText == nil
        let pickerSize = picker.systemLayoutSizeFitting(UIView.layoutFittingCompressedSize)
        let scrollView = createPickerSheetScrollView(picker: picker, pickerSize: pickerSize)
        view.addSubview(cancelButton)
        view.addSubview(doneButton)
        view.addSubview(scrollView)
        let safeArea = view.safeAreaLayoutGuide
        NSLayoutConstraint.activate([
            cancelButton.topAnchor.constraint(equalTo: view.topAnchor, constant: padding),
            cancelButton.leadingAnchor.constraint(equalTo: safeArea.leadingAnchor, constant: padding),
            doneButton.topAnchor.constraint(equalTo: view.topAnchor, constant: padding),
            doneButton.trailingAnchor.constraint(equalTo: safeArea.trailingAnchor, constant: -padding),
            scrollView.topAnchor.constraint(equalTo: doneButton.bottomAnchor),
            scrollView.leadingAnchor.constraint(equalTo: safeArea.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: safeArea.trailingAnchor),
            scrollView.bottomAnchor.constraint(equalTo: safeArea.bottomAnchor)
        ])
        let buttonHeight = doneButton.systemLayoutSizeFitting(UIView.layoutFittingCompressedSize).height
        return padding + buttonHeight + pickerSize.height
    }

    private func createPickerSheetButton(title: String?, configuration: UIButton.Configuration, action: Selector) -> UIButton {
        let minimumTapTargetSize: CGFloat = 44
        var configuration = configuration
        configuration.title = title
        let button = UIButton(configuration: configuration)
        button.translatesAutoresizingMaskIntoConstraints = false
        button.heightAnchor.constraint(greaterThanOrEqualToConstant: minimumTapTargetSize).isActive = true
        button.addTarget(self, action: action, for: .touchUpInside)
        return button
    }

    // Lets the picker scroll if the sheet is shorter than the picker, e.g. in landscape.
    private func createPickerSheetScrollView(picker: UIView, pickerSize: CGSize) -> UIScrollView {
        let scrollView = UIScrollView()
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        picker.translatesAutoresizingMaskIntoConstraints = false
        scrollView.addSubview(picker)
        NSLayoutConstraint.activate([
            scrollView.contentLayoutGuide.widthAnchor.constraint(equalTo: scrollView.frameLayoutGuide.widthAnchor),
            picker.topAnchor.constraint(equalTo: scrollView.contentLayoutGuide.topAnchor),
            picker.bottomAnchor.constraint(equalTo: scrollView.contentLayoutGuide.bottomAnchor),
            picker.centerXAnchor.constraint(equalTo: scrollView.contentLayoutGuide.centerXAnchor),
            picker.widthAnchor.constraint(equalToConstant: pickerSize.width),
            picker.heightAnchor.constraint(equalToConstant: pickerSize.height)
        ])
        return scrollView
    }
}
