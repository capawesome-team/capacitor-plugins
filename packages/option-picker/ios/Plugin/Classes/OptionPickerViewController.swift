import UIKit

class OptionPickerViewController: UIViewController {
    private static let backdropAlpha: CGFloat = 0.4
    private static let headerHeight: CGFloat = 44
    private static let horizontalPadding: CGFloat = 16
    private static let titleSpacing: CGFloat = 8

    private let completion: (PresentResult?, Error?) -> Void
    private let options: PresentOptions
    private let pickerView = UIPickerView()
    private var isFinished = false

    private var initialRow: Int {
        options.options.firstIndex { $0.value == options.value } ?? 0
    }

    init(options: PresentOptions, completion: @escaping (PresentResult?, Error?) -> Void) {
        self.options = options
        self.completion = completion
        super.init(nibName: nil, bundle: nil)
        modalPresentationStyle = .overFullScreen
        modalTransitionStyle = .crossDissolve
        overrideUserInterfaceStyle = options.theme.userInterfaceStyle
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) is not supported")
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        let backdropView = createBackdropView()
        let sheetView = createSheetView()
        view.addSubview(backdropView)
        view.addSubview(sheetView)
        NSLayoutConstraint.activate([
            backdropView.topAnchor.constraint(equalTo: view.topAnchor),
            backdropView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            backdropView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            backdropView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            sheetView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            sheetView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            sheetView.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
        pickerView.selectRow(initialRow, inComponent: 0, animated: false)
    }

    private func createBackdropView() -> UIView {
        let backdropView = UIView()
        backdropView.translatesAutoresizingMaskIntoConstraints = false
        backdropView.backgroundColor = UIColor.black.withAlphaComponent(Self.backdropAlpha)
        backdropView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(handleCancel)))
        return backdropView
    }

    private func createButton(title: String, textStyle: UIFont.TextStyle, action: Selector) -> UIButton {
        let button = UIButton(type: .system)
        button.translatesAutoresizingMaskIntoConstraints = false
        button.setTitle(title, for: .normal)
        button.titleLabel?.font = .preferredFont(forTextStyle: textStyle)
        button.addTarget(self, action: action, for: .touchUpInside)
        return button
    }

    private func createHeaderView() -> UIView {
        let cancelButton = createButton(title: options.cancelButtonText, textStyle: .body, action: #selector(handleCancel))
        let doneButton = createButton(title: options.doneButtonText, textStyle: .headline, action: #selector(handleDone))
        let titleLabel = createTitleLabel()
        let separatorView = createSeparatorView()
        let headerView = UIView()
        headerView.translatesAutoresizingMaskIntoConstraints = false
        headerView.addSubview(cancelButton)
        headerView.addSubview(titleLabel)
        headerView.addSubview(doneButton)
        headerView.addSubview(separatorView)
        NSLayoutConstraint.activate([
            headerView.heightAnchor.constraint(equalToConstant: Self.headerHeight),
            cancelButton.leadingAnchor.constraint(equalTo: headerView.leadingAnchor, constant: Self.horizontalPadding),
            cancelButton.centerYAnchor.constraint(equalTo: headerView.centerYAnchor),
            doneButton.trailingAnchor.constraint(equalTo: headerView.trailingAnchor, constant: -Self.horizontalPadding),
            doneButton.centerYAnchor.constraint(equalTo: headerView.centerYAnchor),
            titleLabel.centerXAnchor.constraint(equalTo: headerView.centerXAnchor),
            titleLabel.centerYAnchor.constraint(equalTo: headerView.centerYAnchor),
            titleLabel.leadingAnchor.constraint(greaterThanOrEqualTo: cancelButton.trailingAnchor, constant: Self.titleSpacing),
            titleLabel.trailingAnchor.constraint(lessThanOrEqualTo: doneButton.leadingAnchor, constant: -Self.titleSpacing),
            separatorView.leadingAnchor.constraint(equalTo: headerView.leadingAnchor),
            separatorView.trailingAnchor.constraint(equalTo: headerView.trailingAnchor),
            separatorView.bottomAnchor.constraint(equalTo: headerView.bottomAnchor),
            separatorView.heightAnchor.constraint(equalToConstant: 1 / traitCollection.displayScale)
        ])
        return headerView
    }

    private func createSeparatorView() -> UIView {
        let separatorView = UIView()
        separatorView.translatesAutoresizingMaskIntoConstraints = false
        separatorView.backgroundColor = .separator
        return separatorView
    }

    private func createSheetView() -> UIView {
        pickerView.dataSource = self
        pickerView.delegate = self
        let stackView = UIStackView(arrangedSubviews: [createHeaderView(), pickerView])
        stackView.translatesAutoresizingMaskIntoConstraints = false
        stackView.axis = .vertical
        let sheetView = UIView()
        sheetView.translatesAutoresizingMaskIntoConstraints = false
        sheetView.backgroundColor = .systemBackground
        sheetView.addSubview(stackView)
        NSLayoutConstraint.activate([
            stackView.topAnchor.constraint(equalTo: sheetView.topAnchor),
            stackView.leadingAnchor.constraint(equalTo: sheetView.safeAreaLayoutGuide.leadingAnchor),
            stackView.trailingAnchor.constraint(equalTo: sheetView.safeAreaLayoutGuide.trailingAnchor),
            stackView.bottomAnchor.constraint(equalTo: sheetView.safeAreaLayoutGuide.bottomAnchor)
        ])
        return sheetView
    }

    private func createTitleLabel() -> UILabel {
        let titleLabel = UILabel()
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        titleLabel.text = options.title
        titleLabel.font = .preferredFont(forTextStyle: .headline)
        titleLabel.textColor = .label
        titleLabel.textAlignment = .center
        titleLabel.lineBreakMode = .byTruncatingTail
        return titleLabel
    }

    private func finish(_ result: PresentResult?, _ error: Error?) {
        if isFinished {
            return
        }
        isFinished = true
        dismiss(animated: true) {
            self.completion(result, error)
        }
    }

    @objc private func handleCancel() {
        finish(nil, CustomError.pickerCanceled)
    }

    @objc private func handleDone() {
        let selectedRow = pickerView.selectedRow(inComponent: 0)
        finish(PresentResult(value: options.options[selectedRow].value), nil)
    }
}

extension OptionPickerViewController: UIPickerViewDataSource, UIPickerViewDelegate {
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }

    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return options.options.count
    }

    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return options.options[row].label
    }
}
